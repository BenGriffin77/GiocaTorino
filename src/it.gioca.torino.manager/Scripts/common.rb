module Common

	def Common.test_var(var)
		
		puts var.class
		case var.class
		
		when Hash
			var.each_pair {|key, value| print "#{key} is #{value}, " }
			puts
		when Array
			var.each { |e| Common.test_var(e) }
		when String
			puts "String: #{var}"
		else
			puts "Cannot test variable"
		end	

	end
	
	
	def Common.dbConnect(config)

		begin
			dbh = Mysql.real_connect(config['dbms'], config['dbuser'], config['dbpass'], 'mysql')
		rescue Mysql::Error
			puts "\t!! Error connecting to DBMS. QUITTING !!"
			exit
		end

		dbh
	end	

end
