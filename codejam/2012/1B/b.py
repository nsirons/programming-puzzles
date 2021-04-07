import heapq


for casen in range(int(input())):
    h,n,m = (int(x) for x in input().split())
    ceiling = [[int(x) for x in input().split()] for _ in range(n)]
    floor = [[int(x) for x in input().split()] for _ in range(n)]

    hp = []
    heapq.heappush(hp, (0, (0,0))) # [time, (x,y)] 
    visited = set()
    ans = -1
    while hp:
        node = heapq.heappop(hp)
        if node[1] == (n-1,m-1):
            ans = node[0] 
            break
        if node[1] in visited:
            continue
        visited.add(node[1])
        x,y = node[1]
        for dx, dy in ((-1,0), (1,0), (0,-1), (0,1)):
            newx = dx + x
            newy = dy + y
            if newx >= 0 and newx < n and newy >= 0 and newy < m:
                if floor[newx][newy] + 50 <= ceiling[x][y] and max(floor[x][y], floor[newx][newy]) + 50 <= ceiling[newx][newy]:
                    t=max(node[0], (h+50-ceiling[newx][newy])/10.)
                    hh = h - t*10
                    if t == 0:
                        dt = 0.
                    elif hh-floor[x][y] >= 20:
                        dt = 1.
                    else:
                        dt = 10.
                    heapq.heappush(hp,(t+dt, (newx, newy)))
    
    print(f"Case #{casen+1}: {ans:.7f}")
