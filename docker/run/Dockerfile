FROM xebialabs/xlr_dev_compile:5.0.1.1

MAINTAINER Joris De Winne <jdewinne@xebialabs.com>
ENV REFRESHED_AT 2016-07-28

ADD resources/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
ADD resources/command.sh /opt/xlr/command.sh

CMD ["/usr/bin/supervisord"]