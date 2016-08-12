FROM xebialabs/xlr_dev_compile:5.0.1.9

MAINTAINER Joris De Winne <jdewinne@xebialabs.com>
ENV REFRESHED_AT 2016-08-12

ADD resources/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
ADD resources/command.sh /opt/xlr/command.sh

RUN apt-get update && apt-get install -y curl

CMD ["/usr/bin/supervisord"]