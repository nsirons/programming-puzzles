for casen in range(int(input())):
    r, c = (int(x) for x in input().split())
    maze = [input().split() for _ in range(r)]
    ans = 0

    def calc_scoremap(maze):
        dp = [[0 for _ in range(len(maze[0]))] for _ in range(len(maze))]
        for i in range(len(maze)):
            for j in range(len(maze[0])):
                if maze[i][j] == '0':
                    dp[i][j] = 0
                else:
                    if i == 0:
                        dp[i][j] = 1
                    else:
                        dp[i][j] = dp[i-1][j] + 1
        return dp

    xp = calc_scoremap(maze)
    yp = calc_scoremap(list(zip(*maze)))
    xm = calc_scoremap(maze[::-1])[::-1]
    ym = calc_scoremap(list(zip(*[row[::-1] for row in maze])))
    yp = list(zip(*yp))
    ym = [row[::-1] for row in list(zip(*ym))]

    for i in range(r):
        for j in range(c):
            ans += max(0,min((xp[i][j])//2, yp[i][j])-1)
            ans += max(0,min((xp[i][j])//2, ym[i][j])-1)
            ans += max(0,min((xm[i][j])//2, yp[i][j])-1)
            ans += max(0,min((xm[i][j])//2, ym[i][j])-1)
            ans += max(0,min((yp[i][j])//2, xp[i][j])-1)
            ans += max(0,min((yp[i][j])//2, xm[i][j])-1)
            ans += max(0,min((ym[i][j])//2, xp[i][j])-1)
            ans += max(0,min((ym[i][j])//2, xm[i][j])-1)

    print(f"Case #{casen+1}: {ans}")
