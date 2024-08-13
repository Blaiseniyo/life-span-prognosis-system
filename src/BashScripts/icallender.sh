#!/bin/bash

# Input date
input_date=$1

# echo "Debug: 1 $input_date"

# Validate date format and convert to ISO format
if [[ "$input_date" =~ ^([0-9]{2})/([0-9]{2})/([0-9]{4})$ ]]; then
    day="${BASH_REMATCH[1]}"
    month="${BASH_REMATCH[2]}"
    year="${BASH_REMATCH[3]}"
    iso_date="${year}-${month}-${day}"
else
    # echo "Debug: Invalid date format. Please use dd/mm/yyyy."
    exit 1
fi

# Validate the date is real
if ! date -d "$iso_date" >/dev/null 2>&1; then
    if ! date -jf "%Y-%m-%d" "$iso_date" >/dev/null 2>&1; then
        # echo "Invalid date. Please enter a valid date."
        exit 1
    fi
fi

# Convert date to required format for iCalendar
if date -d "$iso_date" >/dev/null 2>&1; then
    ical_date=$(date -d "$iso_date" +%Y%m%d)
else
    ical_date=$(date -jf "%Y-%m-%d" "$iso_date" +%Y%m%d)
fi

# Generate a unique identifier
uuid=$(uuidgen 2>/dev/null || cat /proc/sys/kernel/random/uuid 2>/dev/null || echo $(date +%s%N))

# Create iCalendar file
cat << EOF > event.ics
BEGIN:VCALENDAR
VERSION:2.0
PRODID:-//hacksw/handcal//NONSGML v1.0//EN
BEGIN:VEVENT
UID:${uuid}
DTSTAMP:$(date -u +%Y%m%dT%H%M%SZ)
DTSTART;VALUE=DATE:${ical_date}
DTEND;VALUE=DATE:${ical_date}
SUMMARY:Your Funeral Day ${input_date}
DESCRIPTION:Your Funeral Day.
END:VEVENT
END:VCALENDAR
EOF

# echo "iCalendar file 'event.ics' has been created for the date ${input_date}."