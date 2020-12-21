using DataStructures

input_path = joinpath(@__DIR__, "test.in")
data, word_counter,word_unknown_counter = open(input_path) do file
    data = Dict([])
    word_counter = DefaultDict(0)
    word_unknown_counter = DefaultDict(0)
    for line in readlines(file)
        spl1= split(line, " (contains ")
        first_time = true
        for known_word in split(spl1[2][1:end-1], ", ")
            for unknown_word in split(spl1[1]," ")
                if haskey(data,unknown_word)
                    push!(data[unknown_word], known_word)
                else
                    data[unknown_word] = [known_word,]
                end
                if first_time
                    word_unknown_counter[unknown_word] += 1
                end
            end
            first_time = false
            word_counter[known_word] += 1
        end
    end
    (data,word_counter,word_unknown_counter)
end
my_guess = Dict([])


function solve1()
    for (word, cnt) in word_counter
        for (key,val) in data
            if count(i->(i==word),val)==cnt
                if haskey(my_guess, key)
                    push!(my_guess[key], word)
                else
                    my_guess[key] = [word]
                end
            end
        end
    end
    inert_ingredients = (key for (key,val) in data if !haskey(my_guess, key))
    return sum([word_unknown_counter[word] for word in inert_ingredients])
end


function solve2()
    translate = Dict([])
    while length(my_guess) > 0
        for key in [key for (key,_) in my_guess]
            if length(my_guess[key]) == 1
                word = my_guess[key][1]
                translate[word] = key
                for (key1,val) in my_guess
                    my_guess[key1] = [v for v in val if v != word]
                end
                delete!(my_guess, key)
            end
        end
    end
    return join((translate[keyy] for keyy in sort([key for (key,_) in translate])), ",")
end


println("part 1: ", @time solve1())
println("part 2: ", @time solve2())