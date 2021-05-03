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
    n,k = (int(x) for x in input().split())
    lst = sorted((int(x) for x in input().split()))
    diff = sorted((lst[i+1]-lst[i] for i in xrange(n-1)), reverse=True)
    if len(diff) == 0:
        p0,p1 = 0,0
    elif len(diff) == 1:
        p0 = diff[0]
        p1 = 0
    else:
        p0,p1 = diff[:2]

    start = max(0,lst[0]-1)
    end = max(0,k-lst[-1])
    num = max([start+end,start+p0/2,end+p0/2, p0-1, p0/2+p1/2])
    print("Case #{}: {:.9f}".format(casen+1, float(num)/k))
