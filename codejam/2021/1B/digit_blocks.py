# it will pass if you are lucky :)
first_row = []
second_row = []
lst = []

def fr():
    col = first_row.pop()
    print(col)
    lst[col-1].append(val)

def sr():
    col = second_row.pop()
    print(col)
    first_row.append(col)
    lst[col-1].append(val)

def other_r():
    lst[j].append(val)
    print(j+1)
    if len(lst[j]) == b-1:
        first_row.append(j+1)
    elif len(lst[j]) == b-2:
        second_row.append(j+1)
    

t,n,b,p = (int(x) for x in input().split())
for _ in range(t):
    lst = [[] for _ in range(n)]
    first_row = []
    second_row = []
    for _ in range(n*b):
        val = int(input())
        if val == 9:
            if first_row:
                fr()
            elif second_row:
                sr()
            else:
                for j in sorted(range(n), key=lambda x: len(lst[x]), reverse=True):
                    if len(lst[j]) < b-2:
                        other_r()
                        break
                else:
                    assert False
        elif val == 8:
            if second_row:
                sr()
            else:
                for j in sorted(range(n), key=lambda x: len(lst[x]), reverse=True):
                    if len(lst[j]) < b-2:
                        other_r()
                        break
                else:
                    if first_row:
                        fr()
                    else:
                        assert False
        else:
            for j in sorted(range(n), key=lambda x: len(lst[x]), reverse=True):
                if len(lst[j]) < b-2:
                    other_r()
                    break
            else:
                if second_row:
                    sr()
                elif first_row:
                    fr()
                else:
                    assert False
        
input()
