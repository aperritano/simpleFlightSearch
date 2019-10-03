
// https://blog.bitsrc.io/how-to-read-local-json-files-deploy-an-angular-app-a96a881036f2
// https://www.typescriptlang.org/docs/handbook/release-notes/typescript-2-9.html#new---resolvejsonmodule
declare module '*.json' {
  const value: any;
  export default value;
}
