from time import perf_counter


PATH_TO_IDX = {"L": 0, "R": 1}

with open('test.in', 'r') as f:
    data = [line.strip() for line in f.readlines()]


def calc_mag(lst):
    x, y = lst
    ans = 3 * x if isinstance(x, int) else 3 * calc_mag(x)
    ans += 2 * y if isinstance(y, int) else 2 * calc_mag(y)
    return ans


def update(path, val, direction, root):
    next = root
    prev = None
    for ch in path:
        prev = next
        next = next[PATH_TO_IDX[ch]]
        if isinstance(next, int):
            prev[PATH_TO_IDX[ch]] += val
            return

    while not isinstance(next, int):
        prev = next
        next = next[PATH_TO_IDX[direction]]
    prev[PATH_TO_IDX[direction]] += val


def tree_dfs(pair, level, path, root):
    # idea is to keep track of the (tree) path where explosion have happened
    # when explosion detected, you can determine locations where you need to branch out and know which direction to go
    # There are two cases, and for example lets imagine that explosion have happened at the left pair
    # number on the right side would be: up -> right -> left as much as possible (direction)
    # number on the left side would be: go up until you had made turn to right -> up -> left -> right as much as possible (direction)

    if isinstance(pair, int):
        return "nothing", -1, -1, False
    x = pair[0]
    y = pair[1]
    if level >= 4 and isinstance(x, int) and isinstance(y, int):
        return "explode", x, y, True
    
    act = tree_dfs(x, level+1, path + 'L', root)
    if act[0] == "explode":
        # do update
        if act[-1]:
            pair.remove(x)
            pair.insert(0, 0)
            idx = path[::-1].find("R")
            path_left = path[::-1][idx+1:][::-1] + "L"
            path_right = path + "R"
            update(path_left, act[1], 'R', root)
            update(path_right, act[2], 'L', root)
            pair[0] = 0  # overwrite, in case of an edge
        return "explode", -1, -1, False

    act = tree_dfs(y, level+1, path + 'R', root)
    if act[0] == "explode":
        if act[-1]:
            pair.remove(y)
            pair.insert(1, 0)
            path_left = path + "L"
            idx = path[::-1].find("L")
            path_right = path[::-1][idx+1:][::-1] + "R"
            update(path_left, act[1], "R", root)
            update(path_right, act[2], "L", root)
            pair[1] = 0
        return act[0], -1, -1, False
    return "nothing", -1, -1, False


def split_dfs(pair):
    if isinstance(pair, int):
        if pair >= 10:
            return "split", True
        return "nothing", False

    x = pair[0]
    y = pair[1]
    act = split_dfs(x)
    if act[0] == "split":
        if act[-1]:
            pair.remove(x)
            xx = x // 2
            yy = x - xx
            pair.insert(0, [xx, yy])
        return "split", False

    act = split_dfs(y)
    if act[0] == "split":
        if act[-1]:
            pair.remove(y)
            xx = y // 2
            yy = y - xx
            pair.insert(1, [xx, yy])
        return "split", False
    return "nothing", False


def calc(pair):
    while True:
        act = tree_dfs(pair, 0, "", pair)
        if act[0] == "explode":
            continue
        act = split_dfs(pair)
        if act[0] == "split":
            continue
        break
    return pair


t0 = perf_counter()
pair = eval(data[0])
for i in range(1, len(data)):
    pair = [pair, eval(data[i])]
    pair = calc(pair)
part1 = calc_mag(pair)
t1 = perf_counter()

part2 = 0
for i in range(len(data)):
    for j in range(i+1, len(data)):
        part2 = max(part2, calc_mag(calc([eval(data[i]), eval(data[j])])))
        part2 = max(part2, calc_mag(calc([eval(data[j]), eval(data[i])])))

t2 = perf_counter()
print(part1, t1-t0)
print(part2, t2-t1)
