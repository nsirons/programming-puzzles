import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


def solve(x,y):
    goalx = x
    goaly = y
    if (x+y)%2 == 0:
        return "IMPOSSIBLE"
    ans = ""
    while True:
        if x%2 == 1:
            yeven = (y // 2) % 2 == 0
            xp = ((x+1) // 2) % 2 == 0
            if ((yeven and not xp) or (not yeven and xp) or (y==0 and x==-1)) and not (y==0 and x==1):
                ans += "W"
                x = (x+1)//2
            else:
                ans += "E"
                x = (x-1)//2
            y = y // 2
        else:
            xeven = (x // 2) % 2 == 0
            yp = ((y+1) // 2) % 2 == 0
            if ((xeven and not yp) or (not xeven and yp) or (x==0 and y==-1)) and not (x==0 and y==1):
                ans += "S"
                y = (y+1)//2
            else:
                ans += "N"
                y = (y-1)//2
            x = x // 2
        if x == 0 and y == 0:
            break
    return ans


for t in xrange(int(input())):
    x,y = (int(x) for x in input().split())
    print("Case #{}: {}".format(t+1, solve(x,y)))
