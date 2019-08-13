FROM ubuntu:latest

RUN apt-get -qq update
RUN apt-get -qqy install ruby ruby-dev
RUN apt-get -qqy install libreadline-dev libssl-dev zlib1g-dev build-essential bison openssl libreadline6-dev curl git-core zlib1g zlib1g-dev libssl-dev libyaml-dev libsqlite3-0 libsqlite3-dev sqlite3 libxml2-dev libxslt-dev autoconf libc6-dev ncurses-dev


RUN gem install bundler

RUN mkdir -p /app
WORKDIR /app
COPY Gemfile /app/
RUN bundle install --quiet
COPY . /app
EXPOSE 8082
CMD ["bundle", "exec", "rackup", "--host", "0.0.0.0", "-p", "8082"]