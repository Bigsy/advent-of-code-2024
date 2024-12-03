# Similar to (ns day3) in Clojure
defmodule Day3 do
  # Like (defn solve-part1 [input] ...)
  def solve_part1(input) do
    # ~r/.../ is Elixir's regex syntax, similar to #"..." in Clojure
    ~r/mul\((\d{1,3}),(\d{1,3})\)/
    # Like (re-seq pattern input) in Clojure
    |> Regex.scan(input)
    # Similar to (map (fn [[_ x y]] ...) ...)
    |> Enum.map(fn [_, x, y] ->
      # Like (Integer/parseInt) in Clojure for both conversions
      String.to_integer(x) * String.to_integer(y)
    end)
    # Like (reduce + 0 ...) in Clojure
    |> Enum.sum()
  end

  def solve_part2(input) do
    # Multiple regex patterns combined with |, capturing mul(...) or matching do()/don't()
    ~r/mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)/
    |> Regex.scan(input)
    # Pattern matching in function, like Clojure's core.match
    |> Enum.map(fn
      ["do()"] -> :enable      # Keywords in Elixir use : prefix like Clojure
      ["don't()"] -> :disable
      [_, x, y] -> {:mul, String.to_integer(x), String.to_integer(y)}  # Tuple, like a Clojure vector
    end)
    # Map literal %{} is like {} in Clojure. reduce is like reduce
    |> Enum.reduce(%{enabled?: true, sum: 0}, fn
      # When enabled, multiply and add to sum
      {:mul, x, y}, %{enabled?: true, sum: sum} ->
        %{enabled?: true, sum: sum + x * y}
      # When disabled, ignore multiplication
      {:mul, _, _}, acc ->
        acc
      # Enable future operations (like (assoc acc :enabled? true))
      :enable, acc ->
        %{acc | enabled?: true}
      # Disable future operations
      :disable, acc ->
        %{acc | enabled?: false}
    end)
    # Like (:sum result) or (get result :sum) in Clojure
    |> Map.get(:sum)
  end
end
