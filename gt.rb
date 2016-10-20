#!/usr/bin/ruby

require 'rubygems'
require 'mysql'
require 'fileutils'
require 'bgg'
require 'parseconfig'
require 'cld'



def createCollections(text, db, users)

	text.each { |line| 
		data = line.split(',')
		game = data[1]
		
		res = BggApi.search(type: 'boardgame', query: game, exact: 1)
		
		if res['total'].to_i == 0
			res = BggApi.search(type: 'boardgame', query: game, exact: 0)
	
		if res['total'].to_i > 1
			games = res['item']
			primaryGame = games.slice!(0)
			
			print "Best match found: #{primaryGame['name'][0]['value']}, Year: #{primaryGame['yearpublished'][0]['value']}. Confirm? [Y/n]"
			answer = $stdin.gets.chomp
			
			if answer == 'n'
				x = 0
				
				games.each { |game| 
					puts "#{x}. Name: #{game['name'][0]['value']}, Year: #{primaryGame['yearpublished'][0]['value']}"
					x += 1
				}
			
			end
		end
	}
end

def test_var(h)
    h.each_pair {|key, value| puts "#{key} is #{value}" }	
end

def userLookup(text, db)
	names, subnames, uids, users = [], [], [], []

	text.each { |line|
		data = line.split(',')
		names << data[0]
	}

	names.uniq!
	
	names.each { |name|
		ary = []
		choice, x = 0, 0
		res = db.query("SELECT * from users where realname like '%#{name}%' or nickname like '%#{name}%';")
		puts "Searched for #{name}. #{res.num_rows} matches found:"
		#puts res.methods
		#exit
		
		if res.num_rows == 0
			puts "No exact match found. Looking for similar names.."
			
			if name =~ /[^A-Za-z0-9]/
				subnames = name.split(/[^A-Za-z0-9]/)
			elsif (name[1..-1] =~ /[A-Z]/) > 0
				subnames = name.split(/(?=[A-Z])/)
			else 
				subnames << name[1..-2]
			end
		
			name = subnames.slice!(0)
			redo
		elsif res.num_rows > 1
		
			res.each_hash { |row|
				ary << row
				puts "#{x}. Real Name: #{row['realname']}, Nickname: #{row['nickname']}, Email: #{row['email']}"
				x += 1
			}
		
			puts
			print "Multiple matches Found; select the most appropriate one: "
			choice = $stdin.gets.chomp.to_i
	    else
	    	res.each_hash { |row| 
	    		ary << row
	    		puts "Real Name: #{row['realname']}, Nickname: #{row['nickname']}, Email: #{row['email']}" 
	    	}	
		end
		
		uids << ary[choice]['id']
		puts
	}
	
	names.length.times { |i| users << { 'name' => names[i], 'id' => uids[i] } }
	return users
end


def create_tables(db, internaldb)
db.query("create database if not exists #{internaldb};")

    #Set current DB for further operations
	db.select_db("#{internaldb}")
	
	db.query("CREATE TABLE if not exists users (id tinyint(2) unsigned NOT NULL auto_increment, realname char(64), nickname char(32), email char(64), role tinyint(2), available mediumint(6) default 0, PRIMARY KEY (id)) engine=innodb;")

    db.query("CREATE TABLE if not exists collections (id smallint(4) unsigned NOT NULL auto_increment, id_user tinyint(2) unsigned not null, id_boardgame int(11) not null, status enum('checkin', 'ludoteca', 'demo', 'checkout') default 'checkin', document tinyint(2) unsigned default 0, language char(32), PRIMARY KEY (id), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_boardgame) REFERENCES boardgames(ID) on update cascade on delete cascade) engine=innodb;")
	
    db.query("CREATE TABLE if not exists demos (id smallint(4) unsigned not null auto_increment, id_user tinyint(2) unsigned not null, id_collection smallint(4) unsigned not null, start timestamp default 0, end timestamp default 0, PRIMARY KEY (id), INDEX (id_user,id_collection), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_collection) REFERENCES collections(id) on update cascade on delete cascade) engine=innodb;")

end

import = ARGV[0]
config = ParseConfig.new("gt.conf")


#here we connect to target DBMS, select our DB and perform the main procedure to substitute any raw id
begin
    dbh = Mysql.real_connect(config['dbms'], config['dbuser'], config['dbpass'], 'mysql')
rescue Mysql::Error
    puts "\t!! Error connecting to DBMS. QUITTING !!"
    exit
end

create_tables(dbh, config['internaldb'])

list = File.new(import, 'r')
text = list.readlines

users = userLookup(text, dbh)
#users.each { |user| test_var(user); puts }

createCollections(text, dbh, users)










