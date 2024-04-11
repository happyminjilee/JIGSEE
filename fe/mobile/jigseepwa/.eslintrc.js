module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true,
  },
  extends: [
    "plugin:storybook/recommended",
    "next/core-web-vitals",
    "airbnb",
    "airbnb-typescript",
    "plugin:prettier/recommended",
    "plugin:storybook/recommended",
  ],
  overrides: [
    {
      // or whatever matches stories specified in .storybook/main.js
      files: ["*.stories.@(ts|tsx|js|jsx|mjs|cjs)"],
      rules: {
        // example of overriding a rule
        "storybook/hierarchy-separator": "error",
        // example of disabling a rule
        "storybook/default-exports": "off",
      },
    },
  ],
  plugins: ["prettier"],
  parserOptions: {
    project: "tsconfig.json",
    sourceType: "module",
    tsconfigRootDir: __dirname,
  },
  rules: {
    "react/jsx-props-no-spreading": "off",
    "prettier/prettier": ["error", { endOfLine: "auto" }],
  },
  ignores: ["!**/.storybook"],
};
