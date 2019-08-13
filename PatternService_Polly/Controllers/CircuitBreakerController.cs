using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Polly;
using Polly.CircuitBreaker;
using Polly.Fallback;
using Prometheus;

namespace PatternService_Polly.Controllers {

    [Route ("api/[controller]")]
    [ApiController]
    public class CircuitBreakerController : ControllerBase {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly Summary _summary;
        private readonly AsyncFallbackPolicy<String> fallbackForCircuit; 

        public CircuitBreakerController(IHttpClientFactory httpClientFactory, Summary summary)
        {
            _httpClientFactory = httpClientFactory;
            _summary = summary;

            fallbackForCircuit = Policy<String>
                .Handle<BrokenCircuitException>()
                .FallbackAsync(
                    fallbackValue: /* Demonstrates fallback value syntax */ "Please try again later [Fallback for broken circuit]",
                    onFallbackAsync: async b =>
                    {
                        System.Threading.Thread.Sleep(200);
                        Console.WriteLine("circuit breaker open");
                    }
                );
        }
        // GET api/circuitbreaker
        [HttpGet]
        public async Task<ActionResult<string>> Get () {
            using(_summary.WithLabels("/circuitbreaker").NewTimer())
            {
                using (var client = _httpClientFactory.CreateClient("faultservice"))
                {
                    string response = await fallbackForCircuit.ExecuteAsync(() => client.GetStringAsync("/greeting"));
    
                    // only sleep if not fallback
                    if(!response.Equals("Please try again later [Fallback for broken circuit]"))
                    {
                        Console.WriteLine(response);
                        System.Threading.Thread.Sleep(5000);
                    }
                    return response;
                }
            }
        }

    }
}