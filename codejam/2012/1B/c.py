from itertools import combinations


for casen in range(int(input())):
    lst = [int(x) for x in input().split()]
    n = lst[0]
    lst = lst[1:]
    all_options = []
    d = dict()
    ans = None
    for i in range(1,6):
        for combo in combinations(lst, i):
            sc = sum(combo)
            if sc in d:
                ans = d[sc] + "\n" + " ".join(map(str, combo))
                break
            else:
                d[sc] = '\n' + " ".join(map(str, combo))
        if ans is not None:
            break
    else:
        ans = "Impossible"

    print(f"Case #{casen+1}: {ans}")
