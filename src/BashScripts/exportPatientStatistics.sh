#!/bin/bash

input_file="./Database/users.txt"
output_file=$1

# Check if input file exists
if [ ! -f "$input_file" ]; then
    echo "Debug: Input file $input_file not found."
    exit 1
fi

# Initialize variables
total_patients=0
declare -A countries
survival_rates=()

# Current date for age calculation
current_year=$(date +%Y)

# Process the file
while IFS=$'\t' read -r _ _ _ _ role _ birthdate country _ _ _ _ years_remaining; do
    if [ "$role" = "PATIENT" ]; then
        ((total_patients++))
        ((countries[$country]++))
        
        # Calculate current age
        birth_year=$(date -d "$birthdate" +%Y)
        current_age=$((current_year - birth_year))
        
        # Calculate total life expectancy (current age + years remaining)
        total_life_expectancy=$((current_age + years_remaining))
        
        # Calculate survival rate (years remaining / total life expectancy)
        survival_rate=$(awk "BEGIN {printf \"%.4f\", $years_remaining / $total_life_expectancy}")
        survival_rates+=($survival_rate)
    fi
done < "$input_file"

# Sort survival rates for percentile calculations
IFS=$'\n' sorted_rates=($(sort -n <<< "${survival_rates[*]}"))
unset IFS

# Calculate statistics
average=$(awk '{s+=$1} END {printf "%.4f", s/NR}' <<< "${survival_rates[*]}")
median="${sorted_rates[$(( (${#sorted_rates[@]} + 1) / 2 - 1 ))]}"

# Function to calculate percentile
percentile() {
    local p=$1
    local index=$(( (${#sorted_rates[@]} * p / 100) - 1 ))
    echo "${sorted_rates[$index]}"
}

# Write statistics to CSV
{
    echo "Statistic,Value"
    echo "Total Patients,$total_patients"
    echo "Average Survival Rate,$average"
    echo "Median Survival Rate,$median"
    echo "25th Percentile Survival Rate,$(percentile 25)"
    echo "75th Percentile Survival Rate,$(percentile 75)"
    echo "90th Percentile Survival Rate,$(percentile 90)"
    echo "Patients per Country:"
    for country in "${!countries[@]}"; do
        echo "$country,${countries[$country]}"
    done
} > "$output_file"

echo "Debug: Statistics have been saved to $output_file"