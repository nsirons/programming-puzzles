import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


visited = list()
mem = [None]*(10**6+1)


def frac(n):
    f = 2
    lst = []
    while f*f <=n:
        if n%f==0:
            if f != 2:
                lst.append(f)
            if f != n/f:
                lst.append(n/f)
        f += 1
    lst.append(n)
    return sorted(lst)


def rec(lst, n):
    if n < 0:
        return -1 
    if n==0:
        return len(visited)
    ans = 0
    for node in lst:
        if node not in visited and (len(visited) == 0 or node%visited[-1] == 0):
            visited.append(node)
            if mem[n-node] is None:
                mem[n-node] = frac(n-node)
            ans = max(ans, rec(mem[n-node], n-node))
            visited.pop()
    return ans


for casen in xrange(int(input())):
    n = int(input())
    visited = list()
    if mem[n] is None:
        mem[n] = frac(n)
    ans = rec(mem[n],n)
    print("Case #{}: {}".format(casen+1, ans))
