from collections import deque
from time import perf_counter
from itertools import permutations

import numpy as np
from scipy.spatial.transform import Rotation as R
from scipy.spatial import distance_matrix

t0 = perf_counter()

data = []
with open('test.in', 'r') as f:
    lines = f.readlines()
    i = 1
    while i < len(lines):
        row = []
        while i < len(lines) and len(lines[i]) > 2:
            row.append([int(x) for x in lines[i].split(',')])
            i += 1
        data.append(np.array(row).reshape(-1, 3))
        i += 2

rotmax_lst = []
# my solution had +40 matrices instead of 24
# nice idea from:  https://github.com/xdavidliu/advent-code-2021/blob/main/day19.py
for x in [-1, 1]:
    for y in [-1, 1]:
        for z in [-1, 1]:
            for q in permutations([[x, 0, 0], [0, y, 0], [0, 0, z]]):
                m = np.array(q)
                if np.linalg.det(m) == 1:
                    rotmax_lst.append(R.from_matrix(m))

visited = [False for _ in range(len(data))]
coords = [[None, None, None] for _ in range(len(data))]
queue = deque()

start = 0
visited[start] = True
coords[start] = [0, 0, 0]
queue.append(start)

locations = set()
for loc in data[start]:
    locations.add(tuple(loc))

while queue:
    p0 = queue.popleft()
    for p1 in range(len(data)):
        if visited[p1] or p0 == p1:
            continue

        cl1 = data[p0]  # cl == cluster
        ok = False

        for rotmat in rotmax_lst:
            if ok:
                break

            cl2 = rotmat.apply(data[p1])

            # you can skip couple of points, since match should be already found at that point
            for pp0 in range(len(data[p0]) - 11):
                if ok:
                    break
                for pp1 in range(len(data[p1]) - 11):
                    dx = cl2[pp1, :] - cl1[pp0, :]

                    cl3 = cl2 - dx
                    d = distance_matrix(cl3, cl1)

                    if np.sum((d < 1e-6).any(0)) >= 12:  # some numbers are not ints, so some distances can't be exact 0
                        print("MATCH", p0, p1, dx)
                        visited[p1] = True
                        coords[p1] = -dx
                        cl3 = np.rint(cl3)
                        for i in range(cl3.shape[0]):
                            locations.add((cl3[i, 0], cl3[i, 1], cl3[i, 2]))
                        data[p1] = cl3
                        ok = True
                        queue.append(p1)
                        break

part1 = len(locations)
part2 = 0
for i in range(len(coords)):
    x0, y0, z0 = coords[i]
    for j in range(i + 1, len(coords)):
        x1, y1, z1 = coords[j]
        part2 = max(part2, int(abs(x0 - x1) + abs(y0 - y1) + abs(z0 - z1)))

print(str(perf_counter() - t0) + " seconds")
print(part1)
print(part2)
