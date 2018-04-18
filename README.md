# MIYAMOTO
何を作るかは未定

## Localでのサーバー構築方法
今回はDocker Composeを使って簡単に構築できるようにしましたので以下の手順通りにセットアップお願いします。

まず最初はMacにDockerをインストールしてください。以下のページに説明されているので参考までに。
[https://qiita.com/kurkuru/items/127fa99ef5b2f0288b81](https://qiita.com/kurkuru/items/127fa99ef5b2f0288b81)

### 開発環境でのDocker操作
開発環境の起動
```
docker-compose up -d
```

DBのマイグレート
```
docker-compose exec node npm run migrate
```

ログ表示
```
docker-compose logs -f
```

開発環境の終了
```
docker-compose stop
```

Dockerコンテナ内部に入る(SSHみたいな感じ)
```
docker-compose exec node sh
```