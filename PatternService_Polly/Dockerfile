FROM mcr.microsoft.com/dotnet/core/sdk:2.2
WORKDIR /app

# Copy csproj and restore as distinct layers
COPY *.csproj ./
COPY . ./
RUN dotnet restore
ENTRYPOINT ["dotnet", "run"]