using DataStructures

input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end

d = DefaultDict{Tuple{Int64,Int64}, Bool}(false)
direction_map = Dict(
    "e" => (1,0), "w" => (-1,0),
    "ne" => (1,-1), "se" => (0,1),
    "nw" => (0,-1), "sw" => (-1,1),
)


function solve1()
    for line in data
        p = 1
        q,r = 0,0
        while p <= length(line)
            direction = nothing
            if in(line[p], ('e', 'w'))
                direction = line[p] * ""
                p += 1
            else
                direction = line[p:p+1]
                p += 2
            end
            dq,dr = direction_map[direction]
            q += dq
            r += dr
        end
        d[q,r] = !d[q,r]
    end
    return sum((v for (k,v) in d))
end


function solve2(d,direction_map)
    for _=1:100
        flip_lst = []
        for q=-100:100, r=-100:100
            cnt = 0
            for (_,val) in direction_map
                cnt += d[q+val[1], r+val[2]]
                if cnt > 2
                    break
                end
            end

            if d[q,r] && (cnt == 0 || cnt > 2)
                push!(flip_lst, (q,r))
            elseif !d[q,r] && cnt == 2
                push!(flip_lst, (q,r))
            end
        end

        for key in flip_lst
            d[key] = !d[key]
        end
    end
    return sum((v for (_,v) in d))
end


println("part 1: ",@time solve1())
println("part 2: ",@time solve2(d, direction_map))
