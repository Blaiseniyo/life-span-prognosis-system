#!/bin/bash

id=$1
email=$2
firstName=$3
lastName=$4
role=$5
# password=$6
# dob=$7
# countryISOCode=$8
# isHIVPositive=$9
# diagnosisDate=${10}
# isOnART=${11}
# artStartDate=${12}

dob=$6
countryISOCode=$7
isHIVPositive=$8
diagnosisDate=$9
isOnART=${10}
artStartDate=${11}
lifeExpectancy=${12}


# echo "Debug isOnART: $diagnosisDate" >&2
# echo "Debug isOnART: $isOnART" >&2
# # Find the user by email using grep and cut
# user_line=$(grep "$email" ./Database/users.txt)

country_line=$(grep "$countryISOCode" ./Database/life-expectancy.csv)

# echo "Debug: $lifeExpectancy" >&2

# Find the user by email using grep and cut
user_line=$(grep "$id" ./Database/users.txt)

if [ -n "$country_line" ]; then
    hashed_password=$(echo "$user_line" | cut -f6)
    # echo "Debug: ${hashed_password}" >&2
    file_path="./Database/users.txt"

# Use awk to update the line in place
awk -v id="$id" -v email="$email" -v firstName="$firstName" -v lastName="$lastName" \
    -v role="$role" -v hashed_password="$hashed_password" -v dob="$dob" \
    -v countryISOCode="$countryISOCode" -v isHIVPositive="$isHIVPositive" \
    -v diagnosisDate="$diagnosisDate" -v isOnART="$isOnART" \
    -v artStartDate="$artStartDate" -v lifeExpectancy="$lifeExpectancy" '
BEGIN { FS = OFS = "\t" }
{
    if ($1 == id) {
        print id, email, firstName, lastName, role, hashed_password, dob, countryISOCode, isHIVPositive, diagnosisDate, isOnART, artStartDate, lifeExpectancy
    } else {
        print $0
    }
}
' "$file_path" > "$file_path.tmp" && mv "$file_path.tmp" "$file_path"
else
    echo "Invalid country code"
fi