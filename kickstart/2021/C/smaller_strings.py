import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')

M = 10**9 + 7


for casen in xrange(int(input())):
    n,k = (int(x) for x in input().split())
    s = list(input())
    ans = 0
    half = (n+1)//2
    for step in xrange(n):
        if step == half:
            line = list(s[:half])[::-1]
            if n%2 == 0:
                halfi = s[half:]
            else:
                halfi = s[half-1:]
            if line < halfi:
                ans += 1
            break
        opt = ord(s[step])-96
        if opt <= k:
            ans += (opt-1)*pow(k,(half-step-1),M)
        else:
            ans += (min(opt,k) * (pow(k,(half-step-1),M))) %M
            break

    print("Case #{}: {}".format(casen+1, ans%M))
