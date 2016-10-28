#!/usr/bin/ruby

require 'rubygems'
require 'mysql'
require 'fileutils'
require 'bgg'
require 'parseconfig'
require 'cld'
require 'optparse'
require 'pp'
require 'json'

def parseOptions()
	options = {}
	
	OptionParser.new do |opts|
		opts.banner = "Usage: gt.rb -f CSV [options]"

	  	opts.on("-f FILE", "--file FILE", "CSV file to import") do |file|
			options[:file] = file
	  	end
	  	
	  	opts.on("-y", "--yes", "Assume default answer to any question") do |y|
			options[:yes] = y
	  	end
	  	
	  	opts.on_tail("-h", "--help", "Show this message") do
        	puts opts
        	exit
      	end
	    
	end.parse!
	
	raise OptionParser::MissingArgument if options[:file].nil?
	p options
	return options
end

def createCollections(games, db)
	
	games.each { |item|
		puts "INSERT into preloads(user, id_game, language, quantity, id_maingame) values(#{item['id_user']}, #{item['id_boardgame']}, '#{item['language']}', 1, 0);"
		
		begin
			db.query("INSERT into preloads(user, id_game, language, quantity, id_maingame) values(#{item['id_user']}, #{item['id_boardgame']}, '#{item['language']}', 1, 0);")
		rescue Mysql::Error => err
			puts err
			next
		end
	}
	
end

def gameLookup(text, users, options, languages)
	preloads = []
	commonLanguages = ['en', 'fr', 'it',  'de', 'es']
	
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
			
			preloads.each { |item| 
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
			res = BggApi.search(type: 'boardgameexpansion', query: gameQuery, exact: 0) if res['total'].to_i == 0
		
			if res['total'].to_i == 0
				words = gameQuery.split(' ')
				words.slice!(-1)
				newQuery = words.join(' ')
				res = BggApi.search(type: 'boardgame', query: newQuery, exact: 0)
				res = BggApi.search(type: 'boardgameexpansion', query: newQuery, exact: 0) if res['total'].to_i == 0
			end
		
			if res['total'].to_i.between?(1,99)
				games = res['item']
		
				if res['total'].to_i > 1
					#primaryGame = games.slice!(0)
			
					#if primaryGame.has_key? 'yearpublished'
					#	year = ", Year: #{primaryGame['yearpublished'].first['value']}"
					#else
					#	year = ""
					#end
			
					#print "Best match found: #{primaryGame['name'].first['value']}#{year}. Confirm? [Y/n]"
					#answer = $stdin.gets.chomp
			
					if not options.has_key?(:yes)
						x = 0
				
						games.each { |game| 
							#test_var(game)
					
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
				
				if not options.has_key?(:yes)
					if not commonLanguages.include? language[:code]
						puts "Match unreliable. Defaulting to EN"
						language[:code] = "en"
					elsif not language[:reliable]
						print "Language detected is #{language[:name]}\nPlease confirm by pressing ENTER or enter different language ISO code (ie. en,fr,it,de): "
						answer = $stdin.gets.chomp
						
						if answer.length > 1
							languages.each { |lang| 
								if lang['code'] == answer or lang['short'] == answer or lang['name'] =~ /#{answer}/
									language[:name] = lang['name']
									language[:code] = lang['code']
									break
								end
							}
						end
					else
						puts "Language for #{gameName} reliably detected as #{language[:name]}"
					end
				end
						
				title.merge!({ 'gameQuery' => gameQuery, 'id_boardgame' => games[choice]['id'].to_i, 'language' => language[:name]})
			else 
				if res['total'].to_i < 1
					print "No suitable match has been found (typo?). Please insert correct name to search for: "
				else
					print "Too many results. Try refining your search query: "
				end
					
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
		
		preloads << title
		puts

	}
	
	return preloads
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
		res = db.query("SELECT userid as id, realname, username, email from users where realname like '%#{name}%' or username like '%#{name}%';")
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
				puts "#{x}. Realname: #{row['realname']}, username: #{row['username']}, Email: #{row['email']}"
				x += 1
			}
		
			puts
			print "Multiple matches Found; select the most appropriate one: "
			choice = $stdin.gets.chomp.to_i
	    else
	    	res.each_hash { |row| 
	    		ary << row
	    		puts "Realname: #{row['realname']}, username: #{row['username']}, Email: #{row['email']}" 
	    	}	
		end
		
		uids << ary[choice]['id']
		puts
	}
	
	names.length.times { |i| users << { 'name' => names[i], 'id' => uids[i] } }
	return users
end


def create_tables(db, internaldb)
	#db.query("create database if not exists #{internaldb};")

    #Set current DB for further operations
	db.select_db("#{internaldb}")
	
	#db.query("CREATE TABLE if not exists users (id tinyint(2) unsigned NOT NULL auto_increment, realname char(64), nickname char(32), email char(64), role tinyint(2), available mediumint(6) default 0, PRIMARY KEY (id)) engine=innodb;")

    #db.query("CREATE TABLE if not exists preloads (id smallint(4) unsigned NOT NULL auto_increment, id_user tinyint(2) unsigned not null, id_boardgame int(11) not null, status enum('checkin', 'ludoteca', 'demo', 'checkout') default 'checkin', document tinyint(2) unsigned default 0, language char(2), PRIMARY KEY (id), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_boardgame) REFERENCES boardgames(ID) on update cascade on delete cascade) engine=innodb;")
	
    #db.query("CREATE TABLE if not exists demos (id smallint(4) unsigned not null auto_increment, id_user tinyint(2) unsigned not null, id_preloads smallint(4) unsigned not null, start timestamp default 0, end timestamp default 0, PRIMARY KEY (id), INDEX (id_user,id_preloads), FOREIGN KEY (id_user) REFERENCES users(id) on update cascade on delete cascade, FOREIGN KEY (id_preloads) REFERENCES preloads(id) on update cascade on delete cascade) engine=innodb;")

end


trap("INT") {
	puts "\n\n#{Time.now.strftime("%H:%M:%S")}\tCaught INT signal. Closing DB connection.."

	begin
		dbh.close
    rescue
        puts "\t\tConnection already terminated by peer. Exiting"
    ensure
       exit
    end
}



begin
	options = parseOptions()
rescue OptionParser::MissingArgument => err
	puts "#{err}\n"
	ARGV << "-h"
	retry
end

#puts "yes option" if options.has_key?(:yes)
import = options[:file]
config = ParseConfig.new("gt.conf")
languages = JSON(config['languages']).to_ary
#languages.each { |language| test_var(language) }
#exit


#here we connect to target DBMS, select our DB and perform the main procedure to substitute any raw id
begin
    dbh = Mysql.real_connect(config['dbms'], config['dbuser'], config['dbpass'], 'mysql')
rescue Mysql::Error
    puts "\t!! Error connecting to DBMS. QUITTING !!"
    exit
end

create_tables(dbh, config['internaldb'])

begin
	list = File.new(import, 'r')
	text = list.readlines
rescue Errno::ENOENT => err
	puts err
	exit
end

users = userLookup(text, dbh)
#users.each { |user| test_var(user); puts }
puts
games = gameLookup(text, users, options, languages)
createCollections(games, dbh)
puts

#select boardgames.name,boardgames.age,users.realname,users.nickname from preloads inner join users on preloads.id_user = users.id inner join boardgames on preloads.id_boardgame = boardgames.id;









