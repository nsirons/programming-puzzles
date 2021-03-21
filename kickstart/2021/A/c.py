import heapq

def solve(r,c,maze):
    ans = 0
    h = []
    heapq.heapify(h)
    for row in maze:
        for (hi,i,j) in row:
            heapq.heappush(h, (-hi, i,j))
    visited = set()
    while h:
        _,ii,jj = heapq.heappop(h)
        if (ii,jj) not in visited:
            for di,dj in ((1,0),(-1, 0), (0,1), (0,-1)):
                newi = ii + di
                newj = jj + dj
                if newi >= 0 and newi < r and newj >= 0 and newj < c and (newi, newj) not in visited and maze[newi][newj][0] < maze[ii][jj][0]:
                    hh = 0 if abs(maze[ii][jj][0]-maze[newi][newj][0]) <= 1 else abs(maze[ii][jj][0]-maze[newi][newj][0]) - 1
                    ans += hh
                    maze[newi][newj][0] += hh
                    if hh != 0:
                        heapq.heappush(h,(-maze[newi][newj][0], newi, newj))
            visited.add((ii,jj))
            if len(visited) == r*c:
                break
    return ans

for casen in range(int(input())):
    r,c = (int(x) for x in input().split())
    maze = [[[int(x), i,j] for j,x in enumerate(input().split())] for i in range(r) ]
    ans = solve(r,c,maze)
    print(f"Case #{casen+1}: {ans}")
