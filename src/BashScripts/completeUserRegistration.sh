#!/bin/bash

id=$1
email=$2
firstName=$3
lastName=$4
role=$5
password=$6
dob=$7
countryISOCode=$8
isHIVPositive=$9
diagnosisDate=$10
isOnART=$11
artStartDate=${12}


salt=5c10a16899ff96fd


# # Find the user by email using grep and cut
# user_line=$(grep "$email" ./Database/users.txt)

country_line=$(grep "$countryISOCode" ./Database/life-expectancy.csv)

echo "Debug: Grep result: $country_line" >&2

if [ -n "$country_line" ]; then
    hashed_password=$(openssl passwd -6 -salt "$salt"  "$password")
    echo "${hashed_password}"
    file_path="./Database/users.txt"

# Use awk to update the line in place
awk -v id="$id" -v email="$email" -v firstName="$firstName" -v lastName="$lastName" \
    -v role="$role" -v hashed_password="$hashed_password" -v dob="$dob" \
    -v countryISOCode="$countryISOCode" -v isHIVPositive="$isHIVPositive" \
    -v diagnosisDate="$diagnosisDate" -v isOnART="$isOnART" \
    -v artStartDate="$artStartDate" '
BEGIN { FS = OFS = "\t" }
{
    if ($1 == id) {
        print id, email, firstName, lastName, role, hashed_password, dob, countryISOCode, isHIVPositive, diagnosisDate, isOnART, artStartDate
    } else {
        print $0
    }
}
' "$file_path" > "$file_path.tmp" && mv "$file_path.tmp" "$file_path"
else
    echo "Invalid country code"
fi