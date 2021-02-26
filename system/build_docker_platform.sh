#!/bin/bash

adduser --disabled-password --gecos "" buildops
cd /home/buildops
mkdir .ssh

cat << EOF > /home/buildops/.ssh/config
Host *
    StrictHostKeyChecking no
EOF

cat << EOF >> /home/buildops/.ssh/authorized_keys
${USERS_PUBLIC_KEY}
EOF

chmod 600 /home/buildops/.ssh/*
chmod 700 /home/buildops/.ssh
chown -R buildops.buildops /home/buildops/.ssh

echo "%buildops ALL=(ALL) NOPASSWD: LOG_INPUT: ALL"  > /etc/sudoers.d/buildops

apt-get install -y wget curl git python-pip python-dev build-essential tree maven default-jdk

curl -fsSL https://get.docker.com/ | sh

pip install -U pip
pip install --ignore-installed docker-compose

systemctl start docker
systemctl enable docker

usermod -aG docker buildops
