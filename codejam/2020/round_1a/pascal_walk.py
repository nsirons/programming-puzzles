import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


for t in xrange(1, 1 + int(input())):
    n = int(input())
    print("Case #{}:".format(t))
    ans = 0
    if n <= 30:  # trivial solution
        for i in xrange(1,n+1):
            print("{} {}".format(i,1))
    else:
        n_bin = bin(n-30)[2:][::-1]
        r, k = 1,1
        for char in n_bin:
            if char == '0':  # down
                print("{} {}".format(r,k))
                r += 1
                if k != 1:
                    k = r
            else:
                incr = 1 if k == 1 else -1
                for i in xrange(r):
                    print("{} {}".format(r,k))
                    k += incr
                r += 1
                if k == 0:
                    k = 1
                else:
                    k = r
        for i in xrange(30 - n_bin.count('0')):
            print("{} {}".format(r,k))
            r += 1
            if k != 1:
                k = r
