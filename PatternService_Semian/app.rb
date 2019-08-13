require 'sinatra'
require 'mongoid'
require 'net/http'
require 'semian'
require 'semian/net_http'
require 'prometheus/client'


class App < Sinatra::Base

  SEMIAN_PARAMETERS = { tickets: 1,
                        success_threshold: 1,
                        error_threshold: 3,
                        error_timeout: 10,
                        open_circuit_server_errors: true }

  Semian::NetHTTP.semian_configuration = proc do |host, port|
    SEMIAN_PARAMETERS.merge(name: "faultservice_8090")
  end



  
  get '/circuitbreaker' do
    uri = URI('http://faultservice:8090/greeting')
    begin
      response = Net::HTTP.get_response(uri)
      puts response.body
      sleep(5.0)
      response.body
    rescue Net::CircuitOpenError => ex
      puts ex 
      sleep(0.2)
      "Fallback: " + ex.message
    end
  end
end

