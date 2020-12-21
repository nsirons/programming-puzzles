data = [15,12,0,14,3,1]

# not the best way but works in 30 seconds
function solve(n)
    mem = Dict([])
    for i = 1:length(data)
        mem[data[i]] = [i,i]
    end
    last_spoken = data[end]
    for i = length(data)+1:n
        if haskey(mem, last_spoken)
            last_spoken = mem[last_spoken][2] - mem[last_spoken][1]
            if haskey(mem, last_spoken)
                mem[last_spoken][1] = mem[last_spoken][2]
                mem[last_spoken][2] = i
            else
                mem[last_spoken] = [i,i]
            end
        else
            last_spoken = 0
            mem[last_spoken] = [i,i]
        end
    end
    return last_spoken    
end


println("part 1: ", @time solve(2020))
println("part 2: ", @time solve(30000000))