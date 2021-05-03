import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')

from collections import deque


for casen in xrange(int(input())):
    a,b = input().split()
    visited = set()
    q = deque()
    q.append((a,0))
    ans = "IMPOSSIBLE"
    cnt = 0
    L = max(len(a), len(b))
    while q:
        node, cost = q.popleft()
        if node == b:
            ans = cost
            break
        if node in visited:
            continue
        if len(node) > 2*L:  # length limit
            continue 
        visited.add(node)
        if node != '0':
            q.append((node+'0', cost+1))
        asd = ''.join(('1' if x == '0' else '0' for x in node)).lstrip('0')
        if asd == '':
            asd = '0'
        q.append((asd, cost+1))
    print("Case #{}: {}".format(casen+1, ans))
