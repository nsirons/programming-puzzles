def query(x,y):
    print x, y
    return raw_input()


def binary_searchx(x,y,arg):
    first = -10**9
    last = 10**9
    mid = x
    while(first<=last):
        mid = (first + last)//2
        if (first == -10**9 and last == 10**9):
            mid = x
        s = query(mid,y)
        if s == 'CENTER':
            return True
        if s == arg:
            last = mid - 1
        else:
            first = mid + 1
    return mid

def binary_searchy(x,y,arg):
    first = -10**9
    last = 10**9
    mid = y
    while(first<=last):
        mid = (first + last)//2
        if (first == -10**9 and last == 10**9):
            mid = y
        s = query(x,mid)
        if s == 'CENTER':
            return True
        if s == arg:
            last = mid - 1
        else:
            first = mid + 1	
    return mid


tt, a,b = (int(x) for x in raw_input().split())
for t in range(1, tt+1):
    # first hit
    x,y = -10**9+1,-10**9+1
    dstep = 10**9 // 5
    while True:
        s = query(x,y)
        if s != 'MISS':
            break
        x += dstep
        if x > 10**9:
            x = -10**9+1
            y += dstep
    if s == 'CENTER':
        continue
    midx1 = binary_searchx(x,y,'HIT')
    if midx1 is True:
        continue
    
    midx2 = binary_searchx(x,y,'MISS')
    if midx2 is True:
        continue
    
    midy1 = binary_searchy(x,y,'HIT')
    if midy1 is True:
        continue
    
    midy2 = binary_searchy(x,y,'MISS')
    if midy2 is True:
        continue
    
    midx = (midx1 + midx2) // 2  
    midy = (midy1 + midy2) // 2

    sol = False
    for dx in (-1,0,1):
        for dy in (-1,0,1):
            if query(midx + dx, midy + dy) == "CENTER":
                sol = True
                break
        if sol:
            break
