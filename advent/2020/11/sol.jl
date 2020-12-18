# TODO: deepcopy is too slow

input_path = joinpath(@__DIR__, "test.in")
maze = open(input_path) do file
    maze = [collect(line) for line in readlines(file)]
end

dxdy = [(0,1), (1,0), (0,-1), (-1,0), (1,1), (1,-1), (-1,1), (-1,-1)]
h = size(maze)[1]
w = length(maze[1])

function solve1(maze)
    while true
        new_maze = deepcopy(maze)
        for i in 1:size(maze)[1]
            for j in 1:length(maze[1])
                if (maze[i][j] == 'L')
                    ok = true
                    for (dy,dx) in dxdy
                        if (dy+i > 0) && (dx+j > 0) && (dy+i <= h) && (dx+j <= w)
                            if maze[i+dy][j+dx] == '#'
                                ok = false
                                break 
                            end
                        end
                    end
                    if ok
                        new_maze[i][j] = '#'
                    else
                        new_maze[i][j] = 'L'
                    end
                elseif (maze[i][j] == '#')
                    cnt = 0
                    for (dy,dx) in dxdy
                        if (dy+i > 0) && (dx+j > 0) && (dy+i <= h) && (dx+j <= w)
                            if maze[i+dy][j+dx] == '#'
                                cnt += 1
                            end
                        end
                    end
                    if cnt >= 4
                        new_maze[i][j] = 'L'
                    else
                        new_maze[i][j] = '#'
                    end
                else
                    new_maze[i][j] = '.'
                end
            end
        end
        if (new_maze == maze)
            ans = 0
            for line in new_maze
                for c in line
                    if c == '#'
                        ans += 1
                    end
                end
            end
            return ans
        end
        maze = (new_maze)
    end 
end


function solve2(maze)
    while true
        new_maze = deepcopy(maze)
        for i in 1:size(maze)[1]
            for j in 1:length(maze[1])
                if (maze[i][j] == 'L')
                    ok = true
                    for (dy,dx) in dxdy
                        dyy = dy
                        dxx = dx
                        while true
                            if (dyy+i > 0) && (dxx+j > 0) && (dyy+i <= h) && (dxx+j <= w)
                                if maze[i+dyy][j+dxx] == '#'
                                    ok = false
                                    break 
                                elseif maze[i+dyy][j+dxx] == 'L'
                                    break
                                end
                                
                            else
                                break
                            end
                            dyy += dy
                            dxx += dx
                        end
                        if !ok
                            break
                        end
                    end
                    if ok
                        new_maze[i][j] = '#'
                    else
                        new_maze[i][j] = 'L'
                    end
                elseif (maze[i][j] == '#')
                    cnt = 0
                    for (dy,dx) in [(0,1), (1,0), (0,-1), (-1,0), (1,1),(1,-1), (-1,1), (-1,-1)]
                        dyy = dy
                        dxx = dx
                        while true
                            if (dyy+i > 0) && (dxx+j > 0) && (dyy+i <= h) && (dxx+j <= w)
                                if maze[i+dyy][j+dxx] == '#'
                                    cnt += 1
                                    break
                                elseif maze[i+dyy][j+dxx] == 'L'
                                    break
                                end
                            else
                                break
                            end
                            dyy += dy
                            dxx += dx
                        end
                    end
                    if cnt >= 5
                        new_maze[i][j] = 'L'
                    else
                        new_maze[i][j] = '#'
                    end
                else
                    new_maze[i][j] = '.'
                end
            end
        end
        if (new_maze == maze)
            ans = 0
            for line in new_maze
                for c in line
                    if c == '#'
                        ans += 1
                    end
                end
            end
            return ans
        end
        maze = (new_maze)
    end 
end

println("part 1: ", @time solve1(maze))
println("part 2: ", @time solve2(maze))