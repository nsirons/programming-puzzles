using DataStructures

input_path = joinpath(@__DIR__, "test.in")
(cls, me, nearby) = open(input_path) do file
    cls = Dict{String, Set}()
    me = 0
    nearby = []
    iscls = true
    isme = false
    isnearby=true
    for line in eachline(file)
        if iscls
            m = match(r"([a-zA-Z]+\s?[a-zA-Z]*): (\d+)-(\d+) or (\d+)-(\d+)", line)
            if m === nothing
                isme = true
                iscls = false
            else
                a,b,c,d = parse(Int, m[2]),parse(Int, m[3]),parse(Int, m[4]),parse(Int, m[5])
                cls[m[1]] = union(Set([i for i =a:b]), Set([i for i=c:d]))
            end
        elseif isme
            if line != "your ticket:"
                me = [parse(Int, x) for x in split(line, ',')]
                isme = false
                isnerby = true
            end
        else
            if (length(line) > 1) & (strip(line) != "nearby tickets:")
                push!(nearby,[parse(Int, x) for x in split(line, ',')])
            end
        end
    end
    (cls,me,nearby)
end

function solve1()
    ans = 0
    valid_lst = []
    for lst in nearby
        okk = true
        lst_new = []
        for val in lst
            ok = false
            for (key, valset) in cls
                if val in valset
                    ok = true
                    break
                end
            end
            if !ok
                ans += val
                okk = false
            end

        end
        if okk
            push!(valid_lst, lst)
        end
    end
    return ans,valid_lst
end

function solve2(valid_lst)
    ans = 1
    key_per_col = [[] for i in 1:size(valid_lst[1])[1]]
    for (i, lst) in enumerate(valid_lst)
        for (j, val) in enumerate(lst)
            for (key, valset) in cls
                if val in valset
                    push!(key_per_col[j], key)
                end
            end
        end
    end
    solved = [nothing for i in 1:size(valid_lst[1])[1]]
    found = Set{String}()
    while length(found) < length(cls)
        for (j,col) in enumerate(key_per_col)
            full_one = []
            for (key,val) in counter(col)
                if (val == length(valid_lst)) & (!(key in found))
                    push!(full_one, key)
                end
            end 
            if size(full_one)[1] == 1
                push!(found, full_one[1])
                if occursin("departure",full_one[1])
                    ans *= me[j]
                end
            end
        end
    end
    return ans
end


ans1, valid_lst =  @time solve1()
println("part 1: ", ans1)
println("part 2: ", @time solve2(valid_lst))