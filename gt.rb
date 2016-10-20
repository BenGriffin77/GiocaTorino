#!/usr/bin/ruby

require 'rubygems'
require 'mysql'
require 'fileutils'
require 'bgg'
require 'parseconfig'
require 'cld'
require 'optparse'


def createCollections(games, db)
	
	games.each { |item| 
		db.query("INSERT into collections(id_user, id_boardgame, language) values(#{item['id_user']}, #{item['id_boardgame']}, '#{item['language']}');")
	}
	
end

def gameLookup(text, users)
	collections = []
	
	text.each { |line|
		title = {}
		choice = 0
		found = false 
		data = line.split(',')
		name = data.first
		gameQuery = data[1]
		quantity = data[2].to_i
		
		if quantity > 1
			puts "Quantity > 1, checking for existing entry.."
			collections.each { |item| 
				if item['gameQuery'] == gameQuery
					puts "Existing entry has been found"
					found = true
					title.merge!({ 'gameQuery' => gameQuery, 'id_boardgame' => item['id_boardgame'], 'language' => item['language']})
					break
				end 
			}
		end
		
		if not found
			sleep(0.5)
			puts "Searching for #{gameQuery}.."
			res = BggApi.search(type: 'boardgame', query: gameQuery, exact: 1)
			res = BggApi.search(type: 'boardgame', query: gameQuery, exact: 0) if res['total'].to_i == 0
		
			if res['total'].to_i == 0
				words = gameQuery.split(' ')
				words.slice!(-1)
				newQuery = words.join(' ')
				res = BggApi.search(type: 'boardgame', query: newQuery, exact: 0)			
			end
		
			if res['total'].to_i > 0
				games = res['item']
		
				if res['total'].to_i > 1
					primaryGame = games.slice!(0)
			
					if primaryGame.has_key? 'yearpublished'
						year = ", Year: #{primaryGame['yearpublished'].first['value']}"
					else
						year = ""
					end
			
					print "Best match found: #{primaryGame['name'].first['value']}#{year}. Confirm? [Y/n]"
					answer = $stdin.gets.chomp
			
					if answer == 'n'
						x = 0
				
						games.each { |game| 
							test_var(game)
					
							if game.has_key? 'yearpublished'
								year = ", Year: #{game['yearpublished'].first['value']}"
							else
								year = ""
							end
					
							puts "#{x}. Name: #{game['name'].first['value']}#{year}"
							x += 1
						}
			
						puts
						answer = -1
				
						while answer < 0
							print "Multiple matches Found; select the most appropriate one: "
							answer = $stdin.gets.chomp.to_i
						end
				
						choice = answer				
					end
				end
			
				gameName = games[choice]['name'].first['value']
				language = CLD.detect_language(gameName)
		
				if not language[:reliable]
					print "Language detected is #{language[:name]}. Confirm? [Y/n]"
					answer = $stdin.gets.chomp
			
					if answer == 'n'
						print "Please insert title language ISO code (ie. en,fr): "
						language[:code] = $stdin.gets[0..1]
					end
				else
					puts "Language for #{gameName} reliably detected as #{language[:name]}"
				end
		
				title.merge!({ 'gameQuery' => gameQuery, 'id_boardgame' => games[choice]['id'].to_i, 'language' => language[:code]})
			else 
				print "No suitable match has been found (typo?). Please insert correct name to search for: "
				newgame = $stdin.gets.chomp
				puts "gameQuery: #{gameQuery}, line: #{line.chomp}, newgame: #{newgame}"
				line.sub!(/,.*?,/, ",#{newgame},")
				puts line
				redo
			end	
		end
			
		users.each { |user|
			test_var(user)
		 
			if user['name'] == name
				title.merge!({ 'id_user' => user['id'] })
				break
			end
		}
		
		collections << title

	}
	
	return collections
end

def test_var(h)
    h.each_pair {|key, value| print "#{key} is #{value}, " }
    puts	
end

def userLookup(text, db)
	names, subnames, uids, users = [], [], [], []

	text.each { |line|
		data = line.split(',')
		names << data.first
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

    db.query("CREATE TABLE if not exists collections (id smallint(4) unsigned NOT NULL auto_increment, id_user tinyint(2) unsigned not null, id_boardgame int(11) not null, status enum('checkin', 'ludoteca', 'demo', 'checkout') default 'checkin', document tinyint(2) unsigned default 0, language char(2), PRIMARY KEY (id), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_boardgame) REFERENCES boardgames(ID) on update cascade on delete cascade) engine=innodb;")
	
    db.query("CREATE TABLE if not exists demos (id smallint(4) unsigned not null auto_increment, id_user tinyint(2) unsigned not null, id_collection smallint(4) unsigned not null, start timestamp default 0, end timestamp default 0, PRIMARY KEY (id), INDEX (id_user,id_collection), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_collection) REFERENCES collections(id) on update cascade on delete cascade) engine=innodb;")

end

import = ARGV.first
config = ParseConfig.new("gt.conf")


#here we connect to target DBMS, select our DB and perform the main procedure to substitute any raw id
begin
    dbh = Mysql.real_connect(config['dbms'], config['dbuser'], config['dbpass'], 'mysql')
rescue Mysql::Error
    puts "\t!! Error connecting to DBMS. QUITTING !!"
    exit
end

#options = parseOptions
create_tables(dbh, config['internaldb'])

list = File.new(import, 'r')
text = list.readlines

users = userLookup(text, dbh)
#users.each { |user| test_var(user); puts }
puts
games = gameLookup(text, users)
createCollections(games, dbh)
puts

#select boardgames.name,boardgames.age,users.realname,users.nickname from collections inner join users on collections.id_user = users.id inner join boardgames on collections.id_boardgame = boardgames.id;









