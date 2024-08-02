#!/bin/bash

id=$1
email=$2
role=$3

salt=5c10a16899ff96fd

user_line=$(grep "$email" ./Database/users.txt)

if [ -n "$user_line" ]; then
    echo "User already exists"
    exit 1
fi
hashed_password=$(openssl passwd -6 -salt "$salt"  "$password")

echo -e "${id}\t${email}\t\t\t${role}\t\t" >> ./Database/users.txt