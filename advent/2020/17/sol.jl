input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end


function solve1()
    cell_n = 26
    space = zeros(Bool,(cell_n,cell_n,cell_n))
    mid = div(cell_n,2)
    for (x,row) in enumerate(data)
        for (y,col) in enumerate(row)
            if data[x][y] == '#'
                space[mid+x,mid+y,mid+0] = true
            end
        end
    end

    for i=1:6
        new_space = deepcopy(space)
        for x = 2:cell_n-1,y = 2:cell_n-1,z = 2:cell_n-1
            cnt = 0
            for dx in (-1,0,1), dy in (-1,0,1), dz in (-1,0,1)
                if (space[x+dx,y+dy,z+dz] == 1) && !(dx==0 && dy==0 && dz==0)
                    cnt += 1
                    if cnt > 3
                        break
                    end
                end
            end
            if space[x,y,z]
                new_space[x,y,z] = (cnt == 2) || (cnt == 3)
            else
                new_space[x,y,z] = cnt == 3
            end
        end
        space = new_space
    end
    return sum(space)
end

function solve2()
    cell_n = 26
    space = zeros(Bool,(cell_n,cell_n,cell_n,cell_n))
    mid = div(cell_n,2)
    for (x,row) in enumerate(data)
        for (y,col) in enumerate(row)
            space[mid+x,mid+y,mid+0,mid+0] = data[x][y] == '#'
        end
    end

    for i=1:6
        new_space = deepcopy(space)
        for x = 2:cell_n-1,y = 2:cell_n-1,z = 2:cell_n-1, w=2:cell_n-1
            cnt = 0
            for dx in (-1,0,1), dy in (-1,0,1), dz in (-1,0,1), dw in (-1,0,1)
                if (space[x+dx,y+dy,z+dz,w+dw] == 1) && !(dx==0 && dy==0 && dz==0 && dw==0)
                    cnt += 1
                    if cnt > 3
                        break
                    end
                end
            end
            if space[x,y,z,w]
                new_space[x,y,z,w] = (cnt == 2) || (cnt == 3)
            else
                new_space[x,y,z,w] = cnt == 3
            end
        end
        space = new_space
    end
    return sum(space)
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())