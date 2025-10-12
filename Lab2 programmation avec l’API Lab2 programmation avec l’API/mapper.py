#!/usr/bin/env python3
import sys

# Lecture ligne par ligne depuis STDIN
for line in sys.stdin:
    line = line.strip()
    words = line.split()
    for word in words:
        print(f"{word}\t1")
