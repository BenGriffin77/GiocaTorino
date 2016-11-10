#!/usr/bin/ruby

require 'rubygems'
require 'mysql'
require 'fileutils'
require 'bgg'
require 'parseconfig'
require_relative './common'


config = ParseConfig.new("gt.conf")
db = Common.dbConnect(config)

db.select_db(config['internaldb'])

#162225
# and id > 155700
res = db.query("select id,boardgameexpansion from boardgames where boardgameexpansion <> '';")

res.each_hash { |row|
	expansions = row['boardgameexpansion'].split(';')
	puts expansions.inspect	
	
	expansions.each { |eid| 
		sleep(0.1)
		puts "BggApi.thing(type: 'boardgameexpansion', id: #{eid.to_i})"
		r = BggApi.thing(type: 'boardgameexpansion', id: eid.to_i)
	
		if r.has_key?('item')
			newName = r['item'].first['name'].first['value'].gsub(/'/, "''")
			puts "UPDATE boardgames SET name = '#{newName}' where id = #{eid.to_i};"
			db.query("UPDATE boardgames SET name = '#{newName}' where id = #{eid.to_i};")
		else
			puts "UPDATE boardgames SET expansion = 0 where id = #{eid.to_i};"
			db.query("UPDATE boardgames SET expansion = 0 where id = #{eid.to_i};")
		end
	}
	
	#break
}


