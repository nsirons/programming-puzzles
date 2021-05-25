import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


for casen in xrange(int(input())):
    g = int(input())
    ans = 0
    for i in xrange(2000000):  # ~sqrt(10^12)
        l = 1
        r = g
        n = i + 1
        ok = False
        while l <= r:
            mid = (r+l) // 2
            n = i + 1
            ap = (i+1)*(2*mid+n-1) / 2
            if ap == g:
                ok = True
                break
            if ap > g:
                r = mid - 1
            else:
                l = mid + 1
        if ok:
            ans += 1
    print("Case #{}: {}".format(casen+1, ans))
