using DataStructures

input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end

push!(data, "")  # add empty line

function solve1()
    store = Set([])
    ans = 0
    for line in data
        if length(line) == 0
            ans += length(store)
            store = Set([])
        else
            for c in line
                store = push!(store, c)
            end
        end
    end
    return ans
end

function solve2()
    store = DefaultDict(0)
    ans = 0
    cnt = 0
    for line in data
        if length(line) == 0
            for (key,val) in store
                if val == cnt
                    ans += 1
                end
            end
            cnt = 0
            store = DefaultDict(0)
        else
            for c in line
                store[c] += 1
            end
            cnt += 1
        end
    end
    return ans
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())