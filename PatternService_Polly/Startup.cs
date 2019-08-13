using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Diagnostics;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Http;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.AspNetCore.Http;
using Prometheus;
using Polly;
using Polly.CircuitBreaker;
using Polly.Fallback;

namespace PatternService_Polly {
    public class Startup {
        public Startup (IConfiguration configuration) {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices (IServiceCollection services) {
            services.AddMvc ().SetCompatibilityVersion (CompatibilityVersion.Version_2_2);
            
            var requestSummary = Metrics.CreateSummary  ("http_sever_requests_duration_seconds", " The duration of HTTP requests processed by an ASP.NET Core    application.", new SummaryConfiguration{
                LabelNames = new[] {"uri"}
            });;


            var abc = Policy<String>
                .Handle<BrokenCircuitException>();
            services.AddSingleton<Summary>(requestSummary);


            services.AddHttpClient("faultservice", client =>
                {
                    client.BaseAddress = new Uri("http://faultservice:8090");
                })
                .AddTransientHttpErrorPolicy(builder => builder.CircuitBreakerAsync(
                    handledEventsAllowedBeforeBreaking: 4,
                    durationOfBreak: TimeSpan.FromSeconds(3),
                    onBreak: (ex, breakDelay) => {Console.WriteLine("Circuit Breaker: OPEN");},
                    onHalfOpen: () => {Console.WriteLine("Circuit Breaker: HALF OPEN");},
                    onReset: () => {Console.WriteLine("Circuit Breaker: CLOSED");}

                ));
        

        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure (IApplicationBuilder app, IHostingEnvironment env) {
            if (env.IsDevelopment ()) {
                app.UseDeveloperExceptionPage ();
            } else {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts ();
            }

            app.UseHttpsRedirection ();
            app.UseMvc ();

            app.UseMetricServer ();
            
        }
        
    }

    
}