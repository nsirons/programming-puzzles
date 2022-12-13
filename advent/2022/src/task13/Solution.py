from functools import cmp_to_key

with open('test.in', 'r') as f:
    data = [eval(line.strip()) for line in f.readlines() if line.strip()]


def cmp(left, right):
    if isinstance(left, int):
        left = [left]
    if isinstance(right, int):
        right = [right]

    a = len(left)
    b = len(right)

    for i in range(min(a, b)):
        if isinstance(left[i], int) and isinstance(right[i], int):
            if left[i] == right[i]:
                continue
            return 1 if left[i] < right[i] else -1
        else:
            resp = cmp(left[i], right[i])
            if resp == 0:
                continue
            return resp
    if a == b:
        return 0
    return 1 if a < b else -1


part1 = 0
for i in range(len(data) // 2):
    part1 += (i + 1) if cmp(data[2 * i], data[2 * i + 1]) >= 0 else 0
print(part1)

divider_pack = ([[2]], [[6]])
for pack in divider_pack:
    data.append(pack)
part2 = 1
for i, val in enumerate(sorted(data, key=cmp_to_key(cmp), reverse=True)):
    if val in divider_pack:
        part2 *= (i + 1)
print(part2)