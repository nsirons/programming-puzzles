# sorry :( don't have time to make it good
# Capital means you can move it from room to hallway
# lower cased its already been moved from hallway to room, so can't move anymore
import heapq


costd = {'A':1, 'B':10, 'C':100, 'D':1000}
rooms = ["", "", "", ""]
d = {3: 0, 5:1, 7:2, 9:3}
with open('test.in', 'r') as f:
    for i,line in  enumerate(f.readlines()):
        for j,ch in enumerate(line):
            if j in d and ch in costd:
                rooms[d[j]] =  rooms[d[j]] + ch
            

inp1 = list(rooms)

rooms[0] = rooms[0][0] + "DD" + rooms[0][-1]
rooms[1] = rooms[1][0] + "CB" + rooms[1][-1]
rooms[2] = rooms[2][0] + "BA" + rooms[2][-1]
rooms[3] = rooms[3][0] + "AC" + rooms[3][-1]
inp2 = list(rooms)

def solve(rooms):
    arr = ["A", "B", "C", "D"]
    for i, r in enumerate(rooms):
        r = list(r)
        for j in range(len(r)-1,-1,-1):
            if r[j] == arr[i]:
                r[j] = arr[i].lower()
            else:
                break
        rooms[i] = ''.join(r)
    print(rooms)
    hallway = ['.' for _ in range(11)]
    loc = [2,4,6,8]
    hp = []
    heapq.heappush(hp, (0, rooms[0], rooms[1], rooms[2], rooms[3], hallway))
    asd = 0
    maxd = len(rooms[0])

    cost_vis = dict()
    while hp:
        asd += 1
        cost, r1,r2,r3,r4, hallway = heapq.heappop(hp)
        
        if (r1,r2,r3,r4, tuple(hallway)) in cost_vis and cost_vis[(r1,r2,r3,r4,tuple(hallway))] < cost:
            continue
            
        node = [r1,r2,r3,r4]
        
        if r1 == maxd*"a" and r2 == maxd*'b' and r3 == maxd*'c' and r4 == maxd*'d':
            return cost
        
        
        # from hallway to room
        for i, ch in enumerate(hallway):
            if ch != '.':
                try:
                    idx = arr.index(ch)
                except:
                    continue
                
                roomidx = loc[idx]
                
                if roomidx > i:
                    start = i+1
                    end = roomidx+1
                else:
                    start = roomidx
                    end = i
                    
                for j in range(start, end):
                    if hallway[j] != '.':
                        break
                else:
                    wheretogo = node[idx][::-1].find('.')
                    if wheretogo != -1:
                        wheretogo = maxd - wheretogo - 1
                        if not (wheretogo == maxd-1 or node[idx][wheretogo+1].islower()):
                            continue
                        
                        r1_copy = list(node[idx])
                        r1_copy[wheretogo] = ch.lower()
                        r1_copy = ''.join(r1_copy)
                        hallway_copy = list(hallway)
                        hallway_copy[i] = '.'
                        cost_new = cost + costd[ch] * (abs(start-end)+1+wheretogo)
                        output = [cost_new, r1, r2, r3, r4, hallway_copy]
                        output[idx+1] = r1_copy
                        output[-1] = tuple(output[-1])
                        out = tuple(output[1:])
                        
                        if out not in cost_vis or cost_vis[out] > cost_new:
                            cost_vis[out] = cost_new
                            heapq.heappush(hp, output)

        # # move to hallway
        for idx in range(4): # for every room
            for depth in range(maxd):  # for every subroom
                if node[idx][depth] == '.':  # if empty cant move a thing
                    continue
                if node[idx][depth] != '.' and node[idx][depth].isupper() and (depth==0 or node[idx][depth-1] == '.'):  # if not empty and 
                    if hallway[loc[idx]] == '.':   # check that top in good
                        for start,end,dir in [[loc[idx], -1,-1], [loc[idx], 11, 1]]:
                            steps = -1
                            for i in range(start,end,dir):
                                steps += 1
                                if hallway[i] == '.':
                                    if (i in loc):
                                        continue
                                    hallway_copy = list(hallway)
                                    hallway_copy[i] = node[idx][depth]
                                    r1_copy = f'{"."*(depth+1)}{node[idx][depth+1:]}'
                                    cost_new = cost + costd[node[idx][depth]] * (depth+1+steps)
                                    output = [cost_new, r1, r2, r3, r4, hallway_copy]
                                    output[idx+1] = r1_copy
                                    
                                    output[-1] = tuple(output[-1])
                                    out = tuple(output[1:])
                                    
                                    if out not in cost_vis or cost_vis[out] > cost_new:
                                        cost_vis[out] = cost_new
                                        heapq.heappush(hp, output)
                                    
                                else:
                                    break
                else:
                    break
        


print(solve(inp1))
print(solve(inp2))