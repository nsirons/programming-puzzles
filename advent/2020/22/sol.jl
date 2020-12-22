using DataStructures

input_path = joinpath(@__DIR__, "test.in")
player1, player2 = open(input_path) do file
    p2 = false
    player1 = Array{Int}([])
    player2 = Array{Int}([])
    for line in readlines(file)
        if line == "Player 1:"
        elseif length(line) ==0
        elseif line == "Player 2:"
            p2 = true
        elseif !p2
            push!(player1, parse(Int, line))
        elseif p2
            push!(player2, parse(Int, line))
        end
    end
    (player1, player2)
end

function get_score(p1,p2)
    lst = []
    if length(p1) > 0
        while length(p1) > 0
            push!(lst, dequeue!(p1))
        end
    else
        while length(p2) > 0
            push!(lst, dequeue!(p2))
        end 
    end
    return sum([i*val for (i,val) in enumerate(lst[end:-1:1])])
end

function solve1()
    p1 = Queue{Int}()
    for p in player1
        enqueue!(p1,p)
    end
    p2 = Queue{Int}()
    for p in player2
        enqueue!(p2, p)
    end
    while length(p1) > 0 && length(p2) > 0
        a = dequeue!(p1)
        b = dequeue!(p2)
        if a > b
            enqueue!(p1,a)
            enqueue!(p1,b)
        else
            enqueue!(p2,b)
            enqueue!(p2,a)
        end
    end
    return get_score(p1,p2)
end


function solve2(player1, player2, mem, level=1)
    p1 = Queue{Int}()
    for p in player1
        enqueue!(p1,p)
    end
    p2 = Queue{Int}()
    for p in player2
        enqueue!(p2, p)
    end
    while length(p1) > 0 && length(p2) > 0
        state = (Tuple(p1), Tuple(p2))
        if in(state, mem)
            return true
        end
        push!(mem, state)
        
        a = dequeue!(p1)
        b = dequeue!(p2)
        
        if length(p1) >= a && length(p2) >= b
            if solve2(Tuple(p1)[1:a],Tuple(p2)[1:b], copy(mem)) 
                enqueue!(p1,a)
                enqueue!(p1,b)
            else
                enqueue!(p2, b)
                enqueue!(p2,a)
            end
        else
            if a > b
                enqueue!(p1,a)
                enqueue!(p1,b)
            else
                enqueue!(p2, b)
                enqueue!(p2,a)
            end
        end
    end
    return level == 0 ? get_score(p1,p2) : length(p1) > 0
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2(Tuple(player1), Tuple(player2), Set(), 0))  # slow brute-force solution