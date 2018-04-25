module Main where

import Grammar (Expr (..))

main :: IO ()
main = do
  input <- getLine
  putStr input

