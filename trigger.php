<?php

$str = shell_exec("cd /Users/ivan/Documents/Projects/NFLFLAG; mvn clean test");
echo "Result: $str\n\r";