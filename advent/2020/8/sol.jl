input_path = joinpath(@__DIR__, "test.in")
cmd = open(input_path) do file
    cmd = []
    for line in readlines(file)
        m = match(r"(\w+) ([+-]\d+)", line)
        push!(cmd, [m[1], parse(Int, m[2])])
    end
    (cmd)
end


function solve1()
    acc = 0
    visited = [false for _ in 1:size(cmd)[1]]
    curpos = 1
    while !visited[curpos]
        visited[curpos] = true
        if cmd[curpos][1] == "acc"
            acc += cmd[curpos][2]
            curpos += 1
        elseif cmd[curpos][1] == "nop"
            curpos += 1
        else
            curpos += cmd[curpos][2]
        
        end
    end
    return acc
end

function run_sim(cmd)
    # res = false
    acc = 0
    visited = [false for _ in 1:size(cmd)[1]]
    curpos = 1
    while !visited[curpos]
        visited[curpos] = true
        if cmd[curpos][1] == "acc"
            acc += cmd[curpos][2]
            curpos += 1
        elseif cmd[curpos][1] == "nop"
            curpos += 1
        else
            curpos += cmd[curpos][2]
        end
        if curpos > size(cmd)[1]
            return true,acc
        end
    end
    return false, acc
end


function solve2()
    for i in 1:size(cmd)[1]
        if cmd[i][1] == "nop"
            cmd[i][1] = "jmp"
            res,acc = run_sim(cmd)
            if res
                return acc
            end
            cmd[i][1] = "nop"
        elseif cmd[i][1] == "jmp"
            cmd[i][1] = "nop"
            res,acc = run_sim(cmd)
            if res
                return acc
            end
            cmd[i][1] = "jmp"
        end
    end
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())