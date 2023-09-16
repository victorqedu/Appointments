export class ResetPassword {
  constructor(
    public jwtToken: string | null,
    public password: string | null,
  ) {}
}
