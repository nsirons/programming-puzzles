lst = [
    [57, 33, 132, 268, 492, 732],
    [81, 123, 240, 443, 353, 508],
    [186, 42, 195, 704, 452, 228],
    [-7, 2, 357, 452, 317, 395],
    [5, 23, -4, 592, 445, 620],
    [0, 77, 32, 403, 337, 452]
]

S = sum(sum(row) for row in lst)
h = len(lst)
w = len(lst[0])

path = []


def dfs(i, j, front, top, bot, left, right, back, N):
    if i == 0 and j == w - 1:
        s = 0
        for ii, jj in set(tuple(path)):
            s += lst[ii][jj]
        print(f"Found answer - {S - s} in {N} moves")
        print(f"Dice: front:{front}, top:{top}, bot:{bot}, left:{left}, right:{right}, back:{back}")
        print(f"path: {path}")

    for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)):
        ii = dx + i
        jj = dy + j
        if 0 <= ii < h and 0 <= jj < w:
            if dx == -1:  # top
                f, t, b, l, r, ba = bot, front, back, left, right, top
            elif dx == 1:
                f, t, b, l, r, ba = top, back, front, left, right, bot
            elif dy == 1:
                f, t, b, l, r, ba = left, top, bot, back, front, right
            elif dy == -1:
                f, t, b, l, r, ba = right, top, bot, front, back, left
            if f is None:  # side is unknown
                diff = lst[ii][jj] - lst[i][j]
                if diff % N == 0:
                    f = diff // N
                    path.append((ii, jj))
                    dfs(ii, jj, f, t, b, l, r, ba, N + 1)
                    path.pop()
            elif f * N + lst[i][j] == lst[ii][jj]:
                path.append((ii, jj))
                dfs(ii, jj, f, t, b, l, r, ba, N + 1)
                path.pop()
    return


dfs(h - 1, 0, None, None, None, None, None, None, 1)
