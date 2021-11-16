# Spicord

### 説明
🚧開発中🚧   
MinecraftとDiscordとの間でチャットを共有するシンプルなプラグインです。接頭辞の簡易変換、On/Off切り替えなどができます。

### 使い方
・プラグインをMinecraftのプラグインフォルダに入れて一度起動する  
・生成されたconfig.ymlの`token`にDiscord Botのトークン、`guild`にDiscordサーバーのID、`channel`にテキストチャンネルのIDを入れる   
・再び起動する 

### コマンド一覧
#### Minecraft
`/spicord <メッセージ>` メッセージをMinecraftとDiscordの両方に送信します。  
`/toggle (true|false)` 全体チャットを常にDiscordに送信する設定を切り替えることができます。デフォルトはTrueです。   
`/reply` 🚧 
#### Discord
`/mcinfo` リンクしているMinecraftサーバーの情報を取得します。  
`/message <メッセージ>` プライベートメッセージをMinecraftのプレイヤーに送信します。同時に複数人指定することができます。 
