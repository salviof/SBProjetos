#!/bin/bash
echo "Script verificação arquivo p-o-m executado para :caminhoProjetoSource"
cd :caminhoProjetoSource
git ls-remote
find  ./ -name 'pom.xml'