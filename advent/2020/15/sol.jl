data = [15,12,0,14,3,1]

function solve(n)
    mem = Dict{Int, Int}()
    for i in 1:length(data)-1
        mem[data[i]] = i
    end
    last_spoken = data[end]
    for i = length(data):n-1
        val = get(mem,last_spoken,-1)
        mem[last_spoken] = i
        if val != -1
            last_spoken = i - val
        else
            last_spoken = 0
        end
    end
    return last_spoken    
end

println("part 1: ", @time solve(2020))
println("part 2: ", @time solve(30000000))